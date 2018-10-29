# CQRS, DDD, Event sourcing exercise

## Statement

The goal of this exercise is to implement an API endpoint that will process an order received in JSON format. It must validate and persist the order in memory.

Below you have an example of a received order:

```
{
  "order": {
    "id": 1,
    "store_id": 20,
    "lines": [
      {
        "line_number": 1,
        "sku": "blue_sock"
      },
      {
        "line_number": 2,
        "sku": "red_sock"
      }
    ]
  }
}
```

All the validations required before being able to persist the order are listed below:

1. The line numbers of the order items must be sequential (1,2,3...) starting at 1.
2. It's not possible to have two orders with the same id in the same store.
