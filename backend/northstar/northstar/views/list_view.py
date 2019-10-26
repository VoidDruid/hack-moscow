from rest_framework import generics
from rest_framework.response import Response
from northstar.models import *
from northstar.serializers import *
from northstar.models.categories import categories_json
from common.here_api import HERE


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


class OrganisationsListView(generics.ListCreateAPIView):
    """
        Organisations
    """
    queryset = Organization.objects.all()
    serializer_class = OrganizationSerializer


class CategoriesListView(generics.ListAPIView):
    """
        Categories
    """
    serializer_class = None

    def list(self, request, *args, **kwargs):
        return Response(categories_json)


class PlacesListView(generics.ListAPIView):
    """
        Places
    """
    lookup_field = 'uid'
    serializer_class = None

    def list(self, request, *args, **kwargs):
        lat = request.GET.get('lat')
        long = request.GET.get('long')
        places_list = HERE.get_place_by_location({'lat': lat, 'long': long})
        if places_list is not None:
            return Response(places_list)
        else:
            return Response({"ok": False, "Error": "Wrong input parameters"})


class RecommendationsListView(generics.ListAPIView):
    lookup_field = 'uid'
    serializer_class = None

    def list(self, request, *args, **kwargs):
        user = User.objects.filter(uid=kwargs['uid']).first()

        result = UserCategory.objects.filter(user=user).values('category').order_by('-points')[0:3]
        return Response(result)


