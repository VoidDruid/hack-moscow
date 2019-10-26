FROM python:3.7

COPY . /app
WORKDIR /app

RUN pip install -r requirements.txt

EXPOSE 8001

RUN chmod +x run.sh
ENTRYPOINT [ "/app/run.sh" ]
