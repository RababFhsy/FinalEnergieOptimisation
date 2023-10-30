import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { toast } from 'react-toastify';\
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';
import 'app/shared/layout/customStyles/loginForm.scss';
import 'bootstrap/dist/css/bootstrap.min.css';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    []
  );

  const handleValidSubmit = ({ username, email, firstPassword }) => {
    dispatch(handleRegister({ login: username, email, password: firstPassword, langKey: 'en' }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
  <section className="login">
  <div className="login_box">
  <div className="left">
  <div className="contact">
          
          
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
          <h2 id="register-title" className="login-title" data-cy="registerTitle">
            Registration
          </h2>
          <div className="form-group">
            <ValidatedField
              name="username"
              placeholder="Username"
              validate={{
                required: { value: true, message: 'Your username is required.' },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: 'Your username is invalid.',
                },
                minLength: { value: 1, message: 'Your username is required to be at least 1 character.' },
                maxLength: { value: 50, message: 'Your username cannot be longer than 50 characters.' },
              }}
              data-cy="username"
            />
            </div>
            <div className="form-group">
            <ValidatedField
              name="email"
              placeholder="Email"
              type="email"
              validate={{
                required: { value: true, message: 'Your email is required.' },
                minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
                validate: v => isEmail(v) || 'Your email is invalid.',
              }}
              data-cy="email"
            />
            </div>
            <div className="form-group">
            <ValidatedField
              name="firstPassword"
              placeholder="New password"
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, message: 'Your password is required.' },
                minLength: { value: 4, message: 'Your password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your password cannot be longer than 50 characters.' },
              }}
              data-cy="firstPassword"
            />
            </div>
            <PasswordStrengthBar password={password} />
            <div className="form-group">
            <ValidatedField
              name="secondPassword"
              placeholder="Confirm the new password"
              type="password"
              validate={{
                required: { value: true, message: 'Your confirmation password is required.' },
                minLength: { value: 4, message: 'Your confirmation password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your confirmation password cannot be longer than 50 characters.' },
                validate: v => v === password || 'The password and its confirmation do not match!',
              }}
              data-cy="secondPassword"
            />
            </div>
            <button type="submit" data-cy="submit" className="submit">
        Register
      </button>
     
          </ValidatedForm>
          
          </div>
		</div>
    <div className="right">
				<div className="right-text">
        <h2>Energy Optimisation</h2>
					<h5>in smart buildings</h5>
				</div>
				<div className="right-inductor">
          <img src="" alt=""/></div>
			</div>
    </div>
	</section>
    
  );
};

export default RegisterPage;
