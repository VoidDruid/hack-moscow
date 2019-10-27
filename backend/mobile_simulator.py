import json
import time

from requests import Session
import random


class MobileSimulator:
    def __init__(self, period, route):
        self.period = period
        self.session = Session()
        self.route = route
        self.distribution = [0.003 * (x - 50) for x in range(100)]
        self.longs = [37.617664 + x for x in self.distribution]
        self.lats = [55.752121 + x for x in self.distribution]
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
            print(result.json(), data)
            time.sleep(self.period)


ms = MobileSimulator(0.4, 'http://localhost:80/api/location/')
ms.run()
