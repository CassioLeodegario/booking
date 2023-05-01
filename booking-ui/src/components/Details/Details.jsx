import React, { useEffect, useState } from 'react';
import { useParams, useSearchParams } from 'react-router-dom';
import { BiBed, BiBath, BiArea } from 'react-icons/bi';
import axios from 'axios';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { DateRange } from 'react-date-range';
import { useNavigate } from "react-router-dom";
import Aos from 'aos';
import 'aos/dist/aos.css';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { ImSpinner2 } from 'react-icons/im';

import { PLACES_DATA, API_URL } from '../../contansts/contants';

import './details.css';

import agent from '../../assets/house-agent.jpeg'


const Details = ({ setTransparent }) => {
    const [isEdit, setIsEdit] = useState(false);
    const [bookingId, setBookingId] = useState();
    const [place, setPlace] = useState({});
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [unavailableDates, setUnavailableDates] = useState([])
    const [loading, setLoading] = useState(false);
    const [searchParams, setSearchParams] = useSearchParams()
    const navigate = useNavigate();

    const userId = 1;
    const { id } = useParams();

    const selectionRange = {
        startDate,
        endDate,
        key: 'selection',
    }

    const handleSelect = (ranges) => {
        const { startDate, endDate } = ranges.selection

        if (!startDate || !endDate) return

        setStartDate(startDate)
        setEndDate(endDate)
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        const path = isEdit ? `/${bookingId}` : '';
        const method = isEdit ? 'put' : 'post';

        const booking = {
            checkIn: startDate,
            checkOut: endDate,
            placeId: id,
            userId: userId,
        }

        axios({
            method: method,
            url: `${API_URL}/bookings${path}`,
            data: booking
        }).then(function (response) {
            toast.success(`Your place has been successfully ${isEdit ? 'updated' : 'booked'}!`);
            setLoading(false);
            navigate('/list')

        }).catch(function (error) {
            const { response: { data: { message } } } = error;
            toast.error(message);
            setLoading(false);
        });

    }

    useEffect(() => {
        setTransparent('header activeHeader');
        Aos.init({ duration: 2000 })
        const localPlace = PLACES_DATA.find(p => p.id === +id);
        setPlace(localPlace);
    }, [id])

    useEffect(() => {
        const edit = searchParams.get('edit');
        const currentBookingId = searchParams.get('booking');
        setIsEdit(edit);
        setBookingId(currentBookingId);

        const path = edit ? `unavailableDates/${currentBookingId}` : 'unavailableDates';

        axios.get(`${API_URL}/places/${id}/${path}`)
            .then(response => {
                const { data } = response;
                setUnavailableDates(data.map((date) => {
                    const dateArray = date.split('-');
                    const year = dateArray[0];
                    const month = parseInt(dateArray[1], 10) - 1;
                    const day = dateArray[2];
                    return new Date(year, month, day)
                }))
            })
    }, [])

    return (

        <section className='pageContainer'>
            <div className='placeContainer'>
                <div className='placeHeader'>
                    <div >
                        <h2>{place.destTitle}</h2>
                        <h3>{place.address}</h3>
                    </div>

                    <div className='placeDetails'>
                        <div className='placeType'>house</div>
                        <div className='placeCountry'>{place.location}</div>
                    </div>
                    <div className='placePrice'>{place.price}</div>
                </div>

                <div data-aos="fade-right" data-aos-duration="3000" className='placeInfo'>
                    <div className='imageContainer'>
                        <div className='image'>
                            <img src={place.imgSrc} />
                        </div>
                        <div className='amenities'>
                            <div className='singleAmenity'>
                                <BiBed />
                                <div>{place.bedrooms}</div>
                            </div>
                            <div className='singleAmenity'>
                                <BiBath />
                                <div>{place.baths}</div>
                            </div>
                            <div className='singleAmenity'>
                                <BiArea />
                                <div>{place.area}</div>
                            </div>
                        </div>
                    </div>

                    <div data-aos="fade-left" data-aos-duration="3000" className='formWrapper'>
                        <div className='houseAgent'>
                            <div className='agentPicture'>
                                <img src={agent} />
                            </div>
                            <div>
                                <small>Host</small>
                                <div className='agentData'>Cássio Leodegário</div>
                            </div>
                        </div>

                        <form>
                            <DateRange
                                ranges={[selectionRange]}
                                minDate={new Date()}
                                rangeColors={[' hsl(231, 99%, 66%)']}
                                onChange={handleSelect}
                                disabledDates={unavailableDates}
                                moveRangeOnFirstSelection={false}
                            />

                            <div className='flex'>
                                <button onClick={handleSubmit} className='btn'>
                                    {loading ? <ImSpinner2 className="spinnerIcon" /> : 'Book'}
                                </button>
                                <button onClick={() => navigate("/")} className='btn'>Cancel</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </section>

    )
}

export default Details;