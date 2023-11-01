import './home.scss';
import './homeA.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="home-container">
    <Row>
      
      <Col md="9">
      
        
        {account?.login ? (
          <div>
            {/* <Alert color="success">You are logged in as user &quot;{account.login}&quot;.</Alert> */}
          </div>
        ) : (
          <div className="custom-alert-container">
            
          </div>
        )}
        
      
      </Col>
    </Row>
    </div>
  );
};

export default Home;
