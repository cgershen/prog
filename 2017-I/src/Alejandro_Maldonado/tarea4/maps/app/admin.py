from django.contrib import admin
from .models import Place
# Register your models here.
class PlaceModelAdmin(admin.ModelAdmin):
    class Meta:
        model = Place
admin.site.register(Place,PlaceModelAdmin)