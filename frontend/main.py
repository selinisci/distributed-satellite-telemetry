from fastapi import FastAPI, WebSocket
import uvicorn
import json
from aiokafka import AIOKafkaConsumer

app = FastAPI()


KAFKA_TOPIC = "satellite-telemetry-topic"
KAFKA_BOOTSTRAP_SERVERS = "kafka:9092"


async def consume_telemetry(websocket: WebSocket):
    consumer = AIOKafkaConsumer(
        KAFKA_TOPIC,
        bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS,
        value_deserializer=lambda m: json.loads(m.decode("utf-8")),
    )

    await consumer.start()
    try:
        async for msg in consumer:
            
            await websocket.send_json(msg.value)
    finally:
        await consumer.stop()


@app.websocket("/ws")
async def websocket_endpoint(websocket: WebSocket):
    await websocket.accept()
    print(">>> Streamlit interface connected. Listening to Kafka stream...")
    try:
        await consume_telemetry(websocket)
    except Exception as e:
        print(f"Connection lost or Kafka error: {e}")


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
