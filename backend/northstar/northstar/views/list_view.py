from rest_framework import generics
from rest_framework.response import Response
from northstar.models import User, Event
from northstar.serializers import UserSerializer, EventSerializer

from here import HERE


class UserListView(generics.ListCreateAPIView):
    """
        Clients
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer


class EventListView(generics.ListCreateAPIView):
    """
        Events
    """
    queryset = Event.objects.all()
    serializer_class = EventSerializer


class PlacesListView(generics.ListAPIView):
    """
        Places
    """
    lookup_field = 'uid'

    def list(self, request, *args, **kwargs):
        lat = request.GET.get('lat')
        long = request.GET.get('long')
        places_list = HERE.get_place_by_location({'lat': lat, 'long': long})
        if places_list is not None:
            return Response(places_list)
        else:
            return Response({"ok": False, "Error": "Wrong input parameters"})

