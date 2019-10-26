import React from 'react';
import {observer} from "mobx-react";
import {rest} from '../../store/RestStore';
@observer
class EventsPage extends React.Component{
    componentDidMount() {
        rest.getOrganisationsEvents();
    }
    componentWillUnmount() {
        rest.cancelScheduleOrganisationsEventsRequests()
    }

    render() {
        return <div>
            <ul>
            {
                rest.organisationsEventsNames && rest.organisationsEventsNames.map(
                    event => <li>{event}</li>
                )
            }
            </ul>
        </div>
    }
}
export default EventsPage
