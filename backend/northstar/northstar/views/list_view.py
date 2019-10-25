from rest_framework import generics
from northstar.models import User
from northstar.serializers import UserSerializer


class UserListView(generics.ListCreateAPIView):
    """
        Clients
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
