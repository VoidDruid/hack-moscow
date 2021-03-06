# Generated by Django 2.2.4 on 2019-10-26 10:34

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('northstar', '0006_organization_title'),
    ]

    operations = [
        migrations.AlterField(
            model_name='usercategory',
            name='category',
            field=models.CharField(choices=[('EAT_DRINK', 'Eat & Drink'), ('RESTAURANT', 'Restaurant'), ('SNACKS_FAST_FOOD', 'Snacks/Fast food'), ('BAR_PUB', 'Bar/Pub'), ('COFFEE_TEA', 'Coffee/Tea'), ('COFFEE', 'Coffee'), ('TEA', 'Tea'), ('GOING_OUT', 'Going Out'), ('DANCE_NIGHT_CLUB', 'Dance or Nightclub'), ('CINEMA', 'Cinema'), ('THEATRE_MUSIC_CULTURE', 'Theatre, Music & Culture'), ('CASINO', 'Casino'), ('SIGHTS_MUSEUMS', 'Sights & Museums'), ('LANDMARK_ATTRACTION', 'Landmark/Attraction'), ('MUSEUM', 'Museum'), ('TRANSPORT', 'Transport'), ('AIRPORT', 'Airport'), ('RAILWAY_STATION', 'Railway Station'), ('PUBLIC_TRANSPORT', 'Public Transport'), ('FERRY_TERMINAL', 'Ferry Terminal'), ('TAXI_STAND', 'Taxi Stand'), ('ACCOMMODATION', 'Accommodation'), ('HOTEL', 'Hotel'), ('MOTEL', 'Motel'), ('HOSTEL', 'Hostel'), ('CAMPING', 'Camping'), ('SHOPPING', 'Shopping'), ('KIOSK_CONVENIENCE_STORE', 'Kiosk/24-7/Convenience Store'), ('WINE_AND_LIQUOR', 'Wine & Spirits'), ('MALL', 'Shopping Centre'), ('DEPARTMENT_STORE', 'Department Store'), ('FOOD_DRINK', 'Food & Drink'), ('BOOKSHOP', 'Book Shop'), ('PHARMACY', "Chemist's"), ('ELECTRONICS_SHOP', 'Electronics'), ('HARDWARE_HOUSE_GARDEN_SHOP', 'DIY/garden centre'), ('CLOTHING_ACCESSORIES_SHOP', 'Clothing & Accessories'), ('SPORT_OUTDOOR_SHOP', 'Outdoor Sports'), ('SHOP', 'Shop'), ('BUSINESS_SERVICES', 'Business & Services'), ('ATM_BANK_EXCHANGE', 'ATM/Bank/Exchange'), ('POLICE_EMERGENCY', 'Police/Emergency'), ('AMBULANCE_SERVICES', 'Ambulance Services'), ('FIRE_DEPARTMENT', 'Fire Brigade'), ('POLICE_STATION', 'Police Station'), ('POST_OFFICE', 'Post Office'), ('TOURIST_INFORMATION', 'Tourist Information'), ('PETROL_STATION', 'Petrol Station'), ('EV_CHARGING_STATION', 'EV Charging Station'), ('CAR_RENTAL', 'Car Hire'), ('CAR_DEALER_REPAIR', 'Car Dealer/Repair'), ('TRAVEL_AGENCY', 'Travel Agency'), ('COMMUNICATION_MEDIA', 'Communications/Media'), ('BUSINESS_INDUSTRY', 'Business/Industry'), ('SERVICE', 'Service'), ('FACILITIES', 'Facilities'), ('HOSPITAL_HEALTH_CARE_FACILITY', 'Hospital or Healthcare Facility'), ('HOSPITAL', 'Hospital'), ('GOVERNMENT_COMMUNITY_FACILITY', 'Government or Community Facility'), ('EDUCATION_FACILITY', 'Educational Facility'), ('LIBRARY', 'Library'), ('FAIR_CONVENTION_FACILITY', 'Fair & Convention Facility'), ('PARKING_FACILITY', 'Parking Facility'), ('TOILET_REST_AREA', 'Public Toilet/Rest Area'), ('SPORTS_FACILITY_VENUE', 'Sport Facility/Venue'), ('FACILITY', 'Facility'), ('RELIGIOUS_PLACE', 'Religious Place'), ('LEISURE_OUTDOOR', 'Leisure & Outdoor'), ('RECREATION', 'Recreation'), ('AMUSEMENT_HOLIDAY_PARK', 'Theme Park'), ('ZOO', 'Zoo'), ('ADMINISTRATIVE_AREAS_BUILDINGS', 'Administrative Areas/Buildings'), ('ADMINISTRATIVE_REGION', 'Administrative Region'), ('CITY_TOWN_VILLAGE', 'City, Town or Village'), ('OUTDOOR_AREA_COMPLEX', 'Outdoor Area/Complex'), ('BUILDING', 'Building'), ('STREET_SQUARE', 'Street or Square'), ('INTERSECTION', 'Junction'), ('POSTAL_AREA', 'Postal Area'), ('NATURAL_GEOGRAPHICAL', 'Natural or Geographical'), ('BODY_OF_WATER', 'Body of Water'), ('MOUNTAIN_HILL', 'Mountain or Hill'), ('UNDERSEA_FEATURE', 'Undersea Feature'), ('FOREST_HEATH_VEGETATION', 'Forest, Heath or Other Vegetation')], max_length=100),
        ),
    ]
