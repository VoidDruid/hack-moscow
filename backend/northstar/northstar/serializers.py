from rest_framework import serializers
from northstar.models import *


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'uid', 'sex', 'age')

