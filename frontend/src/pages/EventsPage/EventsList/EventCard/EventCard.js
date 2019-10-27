import React, {useState} from 'react'
import './style.css'

export const EventCard = ({title, description, long, lat}) => {
    const [isDisplay, setDisplay] = useState(false);
    return <div className="event-card-container" onClick={() => setDisplay(!isDisplay)}>
        <div className={`event-card-title ${isDisplay? 'event-card-title-active': ''}`}>
            <div>{title}</div>
            <div className="event-card-coords">
                <div className="event-card-lat">{lat}</div>
                <div className="event-card-long">{long}</div>
            </div>
        </div>

            <div className={`event-card-desc ${isDisplay? 'event-card-desc-visible': ''}`}>
                {description}
            </div>
    </div>
}
