import traceback
import herepy
from conf.settings import HERE_APP_CODE, HERE_APP_ID


class HerePlacesApi:
    def __init__(self):
        self.__places_api = herepy.PlacesApi(HERE_APP_ID, HERE_APP_CODE)

    def get_place_by_location(self, location, category=None):
        try:
            if category:
                result = self.__places_api.onebox_search([location['long'], location['lat']], category)
            else:
                result = self.__places_api.places_at([location['long'], location['lat']])
            result_list = []
            for i in range(len(result.as_dict()['results']['items'])):
                element = result.as_dict()['results']['items'][i]
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
