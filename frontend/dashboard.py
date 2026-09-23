import streamlit as st
import asyncio
import websockets
import json
import pandas as pd
import requests
from datetime import datetime

st.set_page_config(page_title="Telemetry Dashboard", layout="wide")
st.title("Real-Time Satellite Telemetry Dashboard")

st.subheader("Listening to Satellite: SAT_TR_01")

col1, col2, col3, col4, col5 = st.columns(5)
temp_placeholder = col1.empty()
signal_placeholder = col2.empty()
battery_placeholder = col3.empty()
voltage_placeholder = col4.empty()
status_placeholder = col5.empty()

st.markdown("### Live Telemetry Trends (Temperature & Signal)")
chart_placeholder = st.empty()

if "telemetry_data" not in st.session_state:
    st.session_state["telemetry_data"] = pd.DataFrame(
        columns=["timestamp", "temperature", "signalStrength"]
    )

    try:
        response = requests.get("http://backend:8080/api/telemetry/history/SAT_TR_01", timeout=3)
        if response.status_code == 200 and response.text:
            history = response.json()
            history.reverse()

            historical_rows = []
            for item in history:
                historical_rows.append(
                    {
                        "timestamp": item.get("timestamp", ""),
                        "temperature": float(item.get("temperature", 0.0)),
                        "signalStrength": float(item.get("signalStrength", 0.0)),
                    }
                )

            if historical_rows:
                st.session_state["telemetry_data"] = pd.DataFrame(historical_rows)
    except Exception as e:
        st.warning(f"Could not fetch historical data from backend: {e}")

# Mevcut verileri ilk açılışta grafikte göster
if not st.session_state["telemetry_data"].empty:
    chart_data = st.session_state["telemetry_data"].set_index("timestamp")
    chart_placeholder.line_chart(chart_data[["temperature", "signalStrength"]])

async def run_websocket():
    uri = "ws://api:8000/ws"
    try:
        async with websockets.connect(uri) as websocket:
            while True:
                data = await websocket.recv()
                telemetry = json.loads(data)

                temp_val = float(telemetry.get("temperature", 0.0))
                sig_val = float(telemetry.get("signalStrength", 0.0))
                battery_val = float(telemetry.get("batteryLevel", 0.0))
                voltage_val = float(telemetry.get("systemVoltage", 0.0))
                is_operational = telemetry.get("isOperational", True)
                timestamp = telemetry.get(
                    "timestamp", datetime.now().strftime("%H:%M:%S")
                )

                status_text = "ONLINE" if is_operational else "OFFLINE"

                temp_placeholder.metric(label="Temperature", value=f"{temp_val:.2f} °C")
                signal_placeholder.metric(
                    label="Signal Strength", value=f"{sig_val:.2f} %"
                )
                battery_placeholder.metric(
                    label="Battery", value=f"{battery_val:.1f} %"
                )
                voltage_placeholder.metric(
                    label="Voltage", value=f"{voltage_val:.2f} V"
                )
                status_placeholder.metric(label="Status", value=status_text)

                new_row = pd.DataFrame(
                    {
                        "timestamp": [timestamp],
                        "temperature": [temp_val],
                        "signalStrength": [sig_val],
                    }
                )

                st.session_state["telemetry_data"] = pd.concat(
                    [st.session_state["telemetry_data"], new_row], ignore_index=True
                )

                if len(st.session_state["telemetry_data"]) > 100:
                    st.session_state["telemetry_data"] = st.session_state[
                        "telemetry_data"
                    ].tail(100)

                chart_data = st.session_state["telemetry_data"].set_index("timestamp")
                chart_placeholder.line_chart(
                    chart_data[["temperature", "signalStrength"]]
                )

    except Exception as e:
        st.error(f"WebSocket connection error: {e}")

try:
    asyncio.run(run_websocket())
except RuntimeError:
    pass