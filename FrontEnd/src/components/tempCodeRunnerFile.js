<Card.Body>
                  <Card.Title>Service Name</Card.Title>
                  <Card.Text>
                    <Card.Title>Customers</Card.Title>
                    <ul>
                      {booking.customers.map(({ name }) => (
                        <li>Name: {name}</li>
                      ))}
                    </ul>
                    <Card.Title>Employees</Card.Title>

                    <ul>
                      {booking.employees.map(({ name }) => (
                        <li>Name: {name} </li>
                      ))}
                    </ul>
                    <Card.Text>Start-Date : {booking.startDateTime}</Card.Text>
                    <Card.Text>End-Date : {booking.endDateTime}</Card.Text>
                  </Card.Text>
                </Card.Body>