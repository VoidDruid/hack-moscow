#!/usr/bin/env bash

until python3 manage.py migrate
do
  echo "DB is unavailable, sleeping"
  sleep 0.5
done

python3 manage.py runserver
