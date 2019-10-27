import React from 'react';
import { Map, TileLayer, Marker, Polygon, Popup} from 'react-leaflet';
import { hereTileUrl } from '../here';
import {observer} from 'mobx-react'
import {mapStore} from "../../../store/MapStore";
import {rest} from "../../../store/RestStore";
import HeatmapLayer from "react-leaflet-heatmap-layer";
import {observable} from "mobx";
import {eventFormStore} from "../../../store/EventFormStore";
@observer
class MapContainer extends React.Component {
    constructor(props) {
        super(props);
        // this.marker = React.createRef();
    }

    // handleDrag = () => {
    //     const coordinates = this.marker.current.leafletElement.getLatLng();
    //     this.props.handleDrag(this.props.index, [coordinates.lat, coordinates.lng]);
    // }

    handleClick = (e) => {
        console.log(e);
        if (!eventFormStore.loc) {
            mapStore.addEventMarker(e.latlng);
        } else {
            mapStore.editLastEventMarker(e.latlng);
        }
        eventFormStore.setLoc(e.latlng);
    };
    render() {
        return (
            <Map
                className="map"
                center={this.props.center}
                zoom={parseInt(this.props.options.zoom)}
                zoomControl={false}
                attributionControl={this.props.index === 8}
                onClick = {this.handleClick}
            >

                {mapStore.isHeatMap && <HeatmapLayer
                    fitBoundsOnLoad
                    fitBoundsOnUpdate
                    points={mapStore.addressPoints}
                    longitudeExtractor={m => m[1]}
                    latitudeExtractor={m => m[0]}
                    intensityExtractor={m => parseFloat(m[2])} />}
                <TileLayer
                    url={hereTileUrl(this.props.style)}
                />
                {mapStore._isMarkersMap && mapStore.eventsMarkers.map((position, idx) =>
                    <Marker key={`marker-${idx}`}
                            position={position}
                            onMouseOver={(e) => {
                                e.target.openPopup();
                            }}
                            onMouseOut={(e) => {
                                e.target.closePopup();
                            }}
                    >
                        <Popup>
                            <span>{mapStore.eventMarkersTitles && mapStore.eventMarkersTitles[idx]}</span>
                        </Popup>
                    </Marker>
                )}
                {/*{mapStore._isMarkersMap && mapStore.eventsMarkers.map(*/}
                {/*    (latlng) => {*/}
                {/*        return <Marker*/}
                {/*        position={latlng}*/}
                {/*        // draggable={true}*/}
                {/*        // onDragEnd={this.handleDrag}*/}
                {/*        ref={this.marker}*/}

                {/*    />}*/}
                )
                    }
                {
                    this.props.polygon.length > 0 &&
                    <Polygon
                        positions={this.props.polygon}
                        color="#2DD5C9"
                    />
                }
            </Map>
        )
    }
}
export default MapContainer
