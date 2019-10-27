import {action, computed, observable, reaction} from "mobx";
import {rest} from './RestStore'
class MapStore {
    @observable _isHeatMap = false;
    @observable _isMarkersMap = false;
    @observable _eventMarkers = [];
    @observable _eventMarkersTitles = [];
    @action toggleHeatMap(){
        this._isHeatMap = !this._isHeatMap;
    }
    @action activateMarkersMap() {
        this._isMarkersMap = true;
    }
    @action deactivateMarkersMap() {
        this._isMarkersMap = false;
    }
    @computed get isMarkersMap() {
        return this._isMarkersMap
    }
    @action
    activateHeatMap(){
        this._isHeatMap = true;
    }
    @action
    deactivateHeatMap(){
        this._isHeatMap = false;
    }
    @action
    disableMarkerMap() {
        this._isMarkersMap = false;
    }
    @computed get isHeatMap(){
        return this._isHeatMap
    }

    @computed get eventsMarkers() {
        return this._eventMarkers
    }
    @action setEventMarkers(markers) {
        this._eventMarkers = markers;
    }
    @action addEventMarker(marker) {
        this._eventMarkers.push(marker);
    }
    @action editLastEventMarker(marker) {
        this._eventMarkers.splice(
            this._eventMarkers.length - 1,
            1,
            marker)
    }
    @action setEventMarkersTitles(titles) {
        this._eventMarkersTitles = titles
    }
    @computed get eventMarkersTitles() {
        return this._eventMarkersTitles
    }
    reactAddressPoints = reaction(() => rest.addressPoints,
        (adresspoints) => {
            this.setEventMarkers(adresspoints.map(({lat, long}) => [lat,long]))
            this.setEventMarkersTitles(adresspoints.map(({title}) => title))
        })
    @computed get addressPoints() {
        return rest.organisationsEvents.map(
            ({lat, long}) => [lat, long, Math.random()*1000]
        )
    }
}
export const mapStore = new MapStore();
