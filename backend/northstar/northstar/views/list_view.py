from datetime import datetime, timezone, timedelta

from django.db.models import Sum
from rest_framework import generics
from rest_framework.response import Response
from northstar.models import *
from northstar.serializers import *
from northstar.models.categories import categories_json
from common.here_api import here


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

    def get_queryset(self):
        category = self.request.GET.get('category')
        if category is None:
            return super().get_queryset()
        return self.queryset.filter(organization__category=category)


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
    queryset = None
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
        category = request.GET.get('category')
        if not category:
            return Response({'ok': False, 'error': "Provide 'category' param"})
        places_list = here.search_q({'lat': lat, 'long': long}, category)
        if places_list is not None:
            return Response(here.format_items(places_list))
        else:
            return Response({'ok': False, 'error': 'Wrong input parameters'})


class RecommendationsListView(generics.ListAPIView):
    lookup_field = 'uid'
    serializer_class = None

    def list(self, request, *args, **kwargs):
        user = User.objects.filter(uid=kwargs['uid']).first()

        result = UserCategory.objects.filter(user=user).values('category').order_by('-points')[0:3]
        return Response(result)


class EventByOrgListView(generics.ListAPIView):
    """
        Event by Organisation
    """
    serializer_class = EventSerializer
    queryset = Event.objects.all()

    def get_queryset(self):
        return self.queryset.filter(organization=self.kwargs['id'])


class CategoryStats(generics.ListAPIView):
    serializer_class = None

    def list(self, request, *args, **kwargs):
        queryset = UserCategory.objects \
            .values('category') \
            .annotate(score=Sum('points')) \
            .order_by('-points')
        return Response({v['category']: v['score'] for v in queryset})


class PlacesStats(generics.ListAPIView):
    serializer_class = None

    def list(self, request, *args, **kwargs):
        boundary = request.GET.get('at')
        if boundary:
            try:
                boundary_date = datetime.fromtimestamp(int(boundary), tz=timezone(timedelta(hours=3)))
            except:
                return Response({'ok': False, 'error': "Could not parse 'at' param"})
            stats = PlacesStatistics.objects \
                .filter(created_at__gte=boundary_date) \
                .order_by('created_at') \
                .first()
        else:
            stats = PlacesStatistics.objects.order_by('-created_at').first()
        result = {}
        if stats:
            result = stats.data
        return Response(result)
