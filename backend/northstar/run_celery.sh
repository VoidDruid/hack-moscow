#!/usr/bin/env bash

PYTHONPATH=..:$PYTHONPATH celery -A northstar worker -B -l info
