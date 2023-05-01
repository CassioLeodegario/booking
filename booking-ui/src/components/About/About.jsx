import React, { useEffect } from 'react';
import Aos from 'aos';
import 'aos/dist/aos.css';

import './about.css';

import img from '../../assets/customer.png';
import img2 from '../../assets/mountain.png';
import img3 from '../../assets/climbing.png';

import video from '../../assets/video.mp4';


const About = () => {

    useEffect(() => {
        Aos.init({duration: 2000})
    }, [])


    return (
        <section className='about section'>
            <div className="secContainer">
                <h3 className="title">
                    Why FullyBooked?
                </h3>

                <div className="mainContent container grid">
                    <div data-aos="fade-up" data-aos-duration="2000" className="singleItem">
                        <img src={img2} alt="" />

                        <h3>100+ Mountains</h3>

                        <p>
                        Explore endless possibilities with over 100 breathtaking mountains to choose from.
                        </p>
                    </div>

                    <div data-aos="fade-up" data-aos-duration="2000" className="singleItem">
                        <img src={img3} alt="" />

                        <h3>1000+ Hikings</h3>

                        <p>
                        Get ready for adventure with over 1000 hiking trails for all levels of expertise.
                        </p>
                    </div>

                    <div data-aos="fade-up" data-aos-duration="2000" className="singleItem">
                        <img src={img} alt="" />

                        <h3>3100+ Customer</h3>

                        <p>
                        Join over 30,000 satisfied customers who trust our services for an unforgettable experience.
                        </p>
                    </div>
                </div>


                <div className="videoCard container">
                    <div className="cardContent grid">
                        <div data-aos="fade-right" data-aos-duration="2000" className="cardText">
                            <h2>
                                Find the best place with us!
                            </h2>

                            <p>
                            Enjoy the ultimate comfort and convenience during your stay with our top-notch amenities and personalized services. Whether you're traveling for business or pleasure, our team is dedicated to ensuring that your experience is nothing short of exceptional. Book your stay today and indulge in the perfect getaway!
                            </p>
                        </div>

                        <div data-aos="fade-left" data-aos-duration="2000" className='cardVideo'>
                            <video src={video} autoPlay loop muted type="video/mp4" ></video>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default About;