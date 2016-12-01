from django.conf import settings
from django.conf.urls import url, include
from django.contrib import admin
from rest_framework_swagger.views import get_swagger_view

schema_view = get_swagger_view(title='PumaRide API')

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api/', include('pumagis.urls', namespace='pumagis')),
    url(r'^docs/', schema_view),
]

