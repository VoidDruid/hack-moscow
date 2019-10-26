import os
import traceback

import herepy
import requests

HERE_APP_ID = os.getenv('HERE_APP_ID', 'gg8kFeV8gw9f89RVtLsN')
HERE_APP_CODE = os.getenv('HERE_APP_CODE', 'KmSywE6j0T04PQ23btBnqA')

if not HERE_APP_CODE or not HERE_APP_ID:
    raise ValueError('Provide HERE app id and code')


class HereWrapper:
    def __init__(self, app_id=HERE_APP_ID, app_code=HERE_APP_CODE):
        self.default_params = {
            'app_id': app_id,
            'app_code': app_code,
        }

    @staticmethod
    def make_at(location: dict) -> str:
        return '{},{}'.format(location['lat'], location['long'])

    def search(self, location: dict):
        request_params = {
            **self.default_params,
            'at': self.make_at(location),
            #'q': query,
        }
        result = requests.get(
            'http://places.cit.api.here.com/places/v1/discover/here',
            params=request_params
        )
        if not result.ok:
            return None
        return result.json()['results']['items'][0]


class HerePlacesApi:
    def __init__(self):
        self.__places_api = herepy.PlacesApi(HERE_APP_ID, HERE_APP_CODE)

    def _place_at(self, location):
        return self.__places_api.places_at([location['lat'], location['long']])

    def get_category_by_location(self, location):
        result = self._place_at(location)
        result = result.as_dict()['results']['items']
        if result:
            return result[0]
        else:
            return None

    def get_place_by_location(self, location, category=None):
        try:
            if category:
                result = self.__places_api.onebox_search([location['lat'], location['long']], category)
            else:
                result = self._place_at(location)
            result_list = []
            result_dict = result.as_dict()
            for i in range(len(result_dict['results']['items'])):
                element = result_dict['results']['items'][i]
                result_list.append({
                        "location": {
                            "long": element['position'][1],
                            "lat": element['position'][0],
                        },
                        "distance": element['distance'],
                        "title": element['title'],
                        "category": element['category']['id']
                })
            return result_list
        except:
            traceback.print_exc()


HERE = HerePlacesApi()
here = HereWrapper()
