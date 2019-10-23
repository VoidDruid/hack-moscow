""" Global URL Configuration """
from django.contrib import admin
from django.urls import include, path

urlpatterns = [
    path('', include("northstar.urls")),
    path('admin/', admin.site.urls),
]
