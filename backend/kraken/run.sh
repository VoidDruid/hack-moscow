#!/bin/bash
PYTHONPATH=..:$PYTHONPATH uvicorn main:app --reload --host='localhost' --port=8001
