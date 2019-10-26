import json
import time

from requests import Session
import random


class MobileSimulator:
    def __init__(self, period, route):
        self.period = period
        self.session = Session()
        self.route = route
        self.coord = [0.015*x for x in range(20)]
        self.longs = [37.61234 + x for x in self.coord]
        self.lats = [55.7535 + x for x in self.coord]
        self.durations = [x for x in range(60)]

    def run(self):
        while True:
            data = {
                "long": random.choice(self.longs),
                "lat": random.choice(self.lats),
                "duration": random.choice(self.durations),
                "city": "Moscow"
            }
            result = self.session.post(self.route + str(random.randint(1, 20)), data=json.dumps(data))
            #print(result.text, data)
            #time.sleep(self.period)


ms = MobileSimulator(1, 'http://localhost:8080/api/location/')
ms.run()
