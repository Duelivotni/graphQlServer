link for opening in browser: http://localhost:8080/graphiql

**CREATE NEW RECORD**
mutation {
  addCustomer(
    input : {
      fullName : "FullName" orders : [{name : "OrderName" price : 99.99}]
    }
  ) {
    fullName
    id
    orders {
      id
      name
      price
    }
  }
}

**READ**
{
  getAllCustomers {
    id
    fullName
    orders {
      id
      name
      price
    }
  }
}

**READ WITH SEARCH PARAMETER**
{
  getCustomersWithOrder(orderName : "OrderName") {
    id
    fullName
    orders {
      id
      name
      price
    }
  }
}
