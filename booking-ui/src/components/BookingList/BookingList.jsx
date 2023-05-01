
import { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './list.css'
import { useNavigate } from 'react-router-dom';
import Dialog from '../Dialog/Dialog';
import { API_URL, PLACES_DATA } from '../../contansts/contants';


const BookingList = ({ setTransparent }) => {
    const [bookings, setBookings] = useState([]);
    const [pageNumber, setPageNumber] = useState(0);
    const [dialog, setDialog] = useState({
        message: "",
        isLoading: false,
        //Update
        itemId: ""
    });

    const handleRemove = (id) => {
        setDialog({
            message: "Are you sure you want to remove this booking?",
            isLoading: true,
            itemId: id
        });
    }

    const confirmDeletion = (choose, itemId) => {
        console.log(itemId)

        if (choose) {
            axios.delete(`${API_URL}/bookings/${itemId}`)
                .then(() => {
                    const bookingIndex = bookings.findIndex((booking) => booking.id === itemId);

                    if (bookingIndex > -1) {
                        let bookingsCopy = Object.assign([], bookings)
                        bookingsCopy.splice(bookingIndex, 1)

                        setBookings(bookingsCopy);
                    }
                    toast.success('Booking removed successfully')

                });
        }
        setDialog({
            message: "",
            isLoading: false,
            itemId: ""
        });
    }

    const navigate = useNavigate();

    const getDataWithPlace = (data) => {
        if (data) {
            return data.map(data => {
                data.place = PLACES_DATA.find(place => +place.id === +data.placeId)
                return data;
            })
        }
        return [];

    }

    const getItemCount = () => {
        if (bookings.length > 1) {
            return `${bookings.length} items`
        }
        return `${bookings.length} item`
    }

    const handleEdit = (e, item) => {
        e.preventDefault();
        navigate({
            pathname: `/details/${item.placeId}`,
            search: `?edit=true&booking=${item.id}`,
        });
    }

    useEffect(() => {
        axios.get(`${API_URL}/bookings`, {
            params: {
                page: pageNumber,
                size: 10,
                sort: 'checkIn',
                userId: 1
            }
        }).then(function (response) {
            const { data } = response.data;
            setBookings(pre => [...pre, ...getDataWithPlace(data)])
        })
    }, [pageNumber])

    useEffect(() => {
        setTransparent('header activeHeader');
        const handleScroll = (e) => {
            const { scrollHeight, scrollTop, clientHeight } = e.target.scrollingElement;
            if (scrollHeight - scrollTop <= clientHeight) {
                setPageNumber(pageNumber + 1);
            }
        }
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [pageNumber])

    return (
        <section className='pageContainer'>
            <div className='listContainer'>
                <div className="tableHeader">
                    <div className="titleContainer">
                        <span className="title">
                            {bookings.length ? 'Your Bookings' : "You don't have bookings yet"}
                        </span>
                        <span>{bookings.length > 0 && getItemCount()}</span>
                    </div>
                </div>

                {bookings.length > 0 &&
                    <div className="tableWrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th>Place</th>
                                    <th>Check-in</th>
                                    <th>Check-out</th>
                                    <th>Price</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {bookings.map(item => (
                                    <tr key={item.id}>
                                        <td>
                                            <div className='flex'>
                                                <img src={item.place.imgSrc} alt="" />
                                                <div className="item">
                                                    <strong>{item.place.destTitle}</strong>
                                                    <small>{item.place.location}</small>
                                                    <small>{item.place.area}</small>
                                                </div>
                                            </div>
                                        </td>
                                        <td>{item.checkIn}</td>
                                        <td>{item.checkOut}</td>
                                        <td> <strong>{item.place.price}</strong></td>
                                        <td>
                                            <button className='btn' onClick={(e) => handleEdit(e, item)}><a href="#">Edit</a></button>
                                            <button className='btn btnRemove' onClick={() => { handleRemove(item.id) }}><a href="#">Remove</a></button>

                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                }
            </div>
            {dialog.isLoading && (
                <Dialog
                    onDialog={confirmDeletion}
                    message={dialog.message}
                    itemId={dialog.itemId}
                />
            )}
        </section>
    )
}

export default BookingList;