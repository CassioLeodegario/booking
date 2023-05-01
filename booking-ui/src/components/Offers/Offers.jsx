import { MdLocationOn } from 'react-icons/md';
import React, { useEffect } from 'react';
import { FaWifi } from 'react-icons/fa';
import { MdKingBed, MdBathtub } from 'react-icons/md';
import { BsArrowRightShort } from 'react-icons/bs';
import { useNavigate } from 'react-router-dom';

import Aos from 'aos';
import 'aos/dist/aos.css';

import { PLACES_DATA } from '../../contansts/contants';

import './offers.css';

const Offers = ({ scrollRef }) => {
    const navigate = useNavigate()

    const handlePlaceClick = (id) => {
        navigate(`/details/${id}`)
    }

    useEffect(() => {
        Aos.init({ duration: 2000 })
    }, [])

    return (
        <section ref={scrollRef} className="offer container section">
            <div className="sectionContainer">

                <div data-aos="fade-up" data-aos-duration="2000" className="secIntro">
                    <h2 className="secTitle">
                        Special Offers
                    </h2>
                    <p>
                        From historical cities to natural specteculars, come see the best of the world.
                    </p>
                </div>


                <div className='mainContent grid'>
                    {PLACES_DATA.map(item => (
                        <div key={item.id} data-aos="fade-up" data-aos-duration="3000" className='singleOffer'>
                            <div className='destImage'>
                                <img src={item.imgSrc} alt={item.destTitle} />

                                <span className='disc'>
                                    {item.discount} off
                                </span>
                            </div>


                            <div className='offerBody'>
                                <div className="price flex">
                                    <h4>{item.price}</h4>

                                    <span className='status'>
                                        for rent
                                    </span>
                                </div>

                                <div className='amenities flex'>
                                    <div className='singleAmenity flex'>
                                        <MdKingBed className='icon' />
                                        <small>4 beds</small>
                                    </div>

                                    <div className='singleAmenity flex'>
                                        <MdBathtub className='icon' />
                                        <small>2 bath</small>
                                    </div>

                                    <div className='singleAmenity flex'>
                                        <FaWifi className='icon' />
                                        <small>Wi-Fi</small>
                                    </div>
                                </div>

                                <div className='location flex'>
                                    <MdLocationOn className='icon' />

                                    <small>{item.address}, {item.location}</small>
                                </div>

                                <button className='btn flex' onClick={() => handlePlaceClick(item.id)}>
                                    View Details
                                    <BsArrowRightShort className='icon' />
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    )
}

export default Offers;