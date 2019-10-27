#!/usr/bin/env bash

until PYTHONPATH=..:$PYTHONPATH python3 manage.py migrate
do
  echo "DB is unavailable, sleeping"
  sleep 0.5
done

PYTHONPATH=..:$PYTHONPATH python3 manage.py runserver
