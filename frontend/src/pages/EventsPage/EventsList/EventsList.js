import React from 'react'
import {observer} from "mobx-react";
import './style.css';
import {rest} from '../../../store/RestStore'
import {EventCard} from "./EventCard/EventCard";
import './style.css'
@observer
class EventsList extends React.Component{

    componentDidMount() {
        rest.getOrganisationsEvents()
    }

    render(){
        return <div className="events-list">
            {
                rest.organisationsEvents.map(
                    event => {
                        return <EventCard {...event}/>
                    }
                )
            }
        </div>
    }
}

export default EventsList;
