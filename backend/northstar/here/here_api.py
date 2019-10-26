import traceback
import herepy
from conf.settings import HERE_APP_CODE, HERE_APP_ID


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
                            "long": element['position'][0],
                            "lat": element['position'][1]
                        },
                        "distance": element['distance'],
                        "title": element['title'],
                        "category": element['category']['id']
                })
            return result_list
        except:
            traceback.print_exc()


HERE = HerePlacesApi()
# print(her.get_place_by_location({'long':37.7905, 'lat':-122.4107}, "restaurant"))
