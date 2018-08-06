"""PumaRide URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin

from pumaride import views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    #url(r'^home/', views.home, name='home'),
    url(r'^$', views.home, name='home'),
    url(r'^index.html', views.home, name='home'),
    url(r'^register.html', views.register, name='register'),
    url(r'^maps.html', views.maps, name='maps'),
    url(r'^restore.html', views.restore, name='restore'),
    url(r'^terms.html', views.terms, name='terms'),
]
