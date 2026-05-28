import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { carAPI, bookingAPI } from '../../api/api';
import './BookingPage.css';

function BookingPage({ user }) {
  const { carId } = useParams();
  const navigate = useNavigate();
  const [car, setCar] = useState(null);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [totalPrice, setTotalPrice] = useState(0);
  const [days, setDays] = useState(0);
  const [loading, setLoading] = useState(true);
  const [bookingLoading, setBookingLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCarDetails();
  }, [carId]);

  // Calculate price when dates change
  useEffect(() => {
    if (car && startDate && endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);
      const calculatedDays = Math.ceil((end - start) / (1000 * 60 * 60 * 24));

      if (calculatedDays > 0) {
        setDays(calculatedDays);
        setTotalPrice(calculatedDays * car.dailyPrice);
      }
    }
  }, [startDate, endDate, car]);

  const fetchCarDetails = async () => {
    try {
      const response = await carAPI.getCarById(carId);
      setCar(response.data);
    } catch (err) {
      setError('Failed to load car details');
    } finally {
      setLoading(false);
    }
  };

  const handleBooking = async (e) => {
    e.preventDefault();

    if (!user) {
      navigate('/login');
      return;
    }

    setBookingLoading(true);
    try {
      const bookingData = {
        startDate,
        endDate,
        totalAmount: totalPrice
      };

      await bookingAPI.createBooking(user.id, carId, bookingData);

      // Success - redirect to my bookings
      navigate('/bookings');
    } catch (err) {
      setError(err.response?.data?.message || 'Booking failed');
    } finally {
      setBookingLoading(false);
    }
  };

  if (loading) return <div className="booking-loading">
    <div className="spinner"></div>
    <p>Loading car details...</p>
  </div>;
  if (error) return <div className="error-banner">{error}</div>;
  if (!car) return <div className="error-banner">Car not found</div>;

  return (
    <div className="booking-page">
      <div className="booking-container">
        <button className="back-button" onClick={() => navigate('/')}>
          <span>←</span> Back to Cars
        </button>

        <div className="booking-header">
          <h1>Complete Your Booking</h1>
          <p>Review car details and select your rental dates</p>
        </div>

        <div className="booking-grid">
          {/* Car Details Section */}
          <div className="car-showcase">
            <div className="car-image-box">
              {car.imageUrl ? (
                <img src={car.imageUrl} alt={`${car.brand} ${car.model}`} className="car-image" />
              ) : (
                <div className="car-placeholder">🚗</div>
              )}
            </div>

            <div className="car-header-info">
              <div className="car-title">
                <h2>{car.brand}</h2>
                <h3>{car.model}</h3>
              </div>
              <div className="car-status-badge">{car.status}</div>
            </div>

            <div className="car-specs">
              <div className="spec-item">
                <span className="spec-icon">📅</span>
                <div>
                  <p className="spec-label">Year</p>
                  <p className="spec-value">{car.year}</p>
                </div>
              </div>
              <div className="spec-item">
                <span className="spec-icon">🏎️</span>
                <div>
                  <p className="spec-label">Type</p>
                  <p className="spec-value">{car.type}</p>
                </div>
              </div>
              <div className="spec-item">
                <span className="spec-icon">⛽</span>
                <div>
                  <p className="spec-label">Fuel Type</p>
                  <p className="spec-value">{car.fuelType}</p>
                </div>
              </div>
              <div className="spec-item">
                <span className="spec-icon">💰</span>
                <div>
                  <p className="spec-label">Daily Rate</p>
                  <p className="spec-value highlight">${car.dailyPrice}/day</p>
                </div>
              </div>
            </div>
          </div>

          {/* Booking Form Section */}
          <div className="booking-form-section">
            <form className="booking-form" onSubmit={handleBooking}>
              <div className="form-section">
                <h3>📍 Select Rental Period</h3>
                
                <div className="date-inputs">
                  <div className="form-group">
                    <label htmlFor="start-date">
                      <span className="label-icon">📦</span> Pick-up Date
                    </label>
                    <input
                      id="start-date"
                      type="date"
                      value={startDate}
                      onChange={(e) => setStartDate(e.target.value)}
                      required
                      min={new Date().toISOString().split('T')[0]}
                      className="date-input"
                    />
                  </div>

                  <div className="form-group">
                    <label htmlFor="end-date">
                      <span className="label-icon">📤</span> Return Date
                    </label>
                    <input
                      id="end-date"
                      type="date"
                      value={endDate}
                      onChange={(e) => setEndDate(e.target.value)}
                      required
                      min={startDate || new Date().toISOString().split('T')[0]}
                      className="date-input"
                    />
                  </div>
                </div>
              </div>

              {/* Price Breakdown */}
              <div className="price-breakdown">
                <h3>💵 Price Breakdown</h3>
                
                <div className="breakdown-item">
                  <span className="breakdown-label">Daily Rate</span>
                  <span className="breakdown-value">${car.dailyPrice}</span>
                </div>

                {days > 0 && (
                  <>
                    <div className="breakdown-item">
                      <span className="breakdown-label">Number of Days</span>
                      <span className="breakdown-value">{days} day{days > 1 ? 's' : ''}</span>
                    </div>

                    <div className="breakdown-divider"></div>

                    <div className="breakdown-item total">
                      <span className="breakdown-label">Total Amount</span>
                      <span className="breakdown-total">${totalPrice.toFixed(2)}</span>
                    </div>
                  </>
                )}
              </div>

              {/* Booking Info */}
              <div className="booking-info">
                <div className="info-item">
                  <span className="info-icon">✓</span>
                  <p>Free cancellation up to 24 hours before pickup</p>
                </div>
                <div className="info-item">
                  <span className="info-icon">✓</span>
                  <p>Full insurance coverage included</p>
                </div>
                <div className="info-item">
                  <span className="info-icon">✓</span>
                  <p>24/7 roadside assistance</p>
                </div>
              </div>

              <button
                type="submit"
                disabled={bookingLoading || !startDate || !endDate}
                className="submit-button primary-button"
              >
                {bookingLoading ? (
                  <>
                    <span className="spinner-mini"></span>
                    Processing...
                  </>
                ) : (
                  <>
                    <span>🎉</span>
                    Confirm Booking
                  </>
                )}
              </button>

              <p className="form-note">
                By clicking confirm, you agree to our terms and conditions
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default BookingPage;
