import React from 'react'
import './style.css'
class StatisticsCard extends React.Component {

    render() {
        let {location, name} = this.props;
        return <div className="statistics-card-container">
            <div className="statistics-card-name">
                {name}
            </div>
                <div className="statistics-card-location">
                    <div className="statistics-card-location-lat">
                        {location.lat}
                    </div>
                    <div className="statistics-card-location-long">
                        {location.long}
                    </div>
                </div>
        </div>
    }
}

export default StatisticsCard;
