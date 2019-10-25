""" Global URL Configuration """
from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from django.contrib import admin
from django.urls import include, path

schema_view = get_schema_view(
   openapi.Info(
      title="Northstar API",
      default_version='v1',
      description="Northstar API Swagger",
   ),
   public=True,
)

urlpatterns = [
    path('', include("northstar.urls")),
    path('admin/', admin.site.urls),
    # Swagger
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
]
