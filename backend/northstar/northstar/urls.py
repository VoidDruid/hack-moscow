""" App URL Configuration """
from django.urls import path

from northstar.views import *

urlpatterns = [
    path('users', UserListView.as_view()),
    path('users/<str:uid>', UserDetailView.as_view()),
]
