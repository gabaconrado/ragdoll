# Ragdoll project

## Motivation

Learn new technologies and sharp my programming skills.

## Objective

Provide a system to help in the management of the company `Toalhas limpinhas`.

## Description

### The company

The company borrows towels to petshops in **Bauru - SP**, it profits from the use of those towels.

Things that cost money:

- New towels
- Towel wash
- Towel maintenance
- Towel discard
- Towel packing
- Towel delivery

Things that provide money:

- Towel borrow

### System requisites

The system idealy should have following characteristics:

- Manage towels:
  - Control
  - Towel models
- Manage clients:
  - Control
  - Data feedback
- Manage sales:
  - Control
  - Integrate and ease the towel control/distribution for all the clients
  - Financial documentation
- Manage finances:
  - Company balance
  - Data feedback

### Technologies

Based on the requisites, the architecture will probably be an Android app with cloud communication.

## Entities

```nomnoml
[Finances|balance : real]
[Towel|type: string; amount: integer;available: integer]
[Client|name: string; address: string; phone: string; email: string; balance: real| [Towel|towels:list; price: real]]
[Sale|date: date; value: real|
    [Client];[Towel|quantity_borrowed: integer; quantity_collected: integer]
]
```

## Use cases

```nomnoml
[<usecase> Manage towels] - [<actor>User]
[<usecase> Manage clients] - [<actor>User]
[<usecase> Execute sales] - [<actor>User]
[<usecase> Check reports] - [<actor>User]
```

## Architecture

```nomnoml
[<database> web server] - [<actor> Android App]
[<database> web server] - [<actor> Web app]
```