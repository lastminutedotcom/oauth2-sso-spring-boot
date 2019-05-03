# Distributed authorization in Microservices with Spring

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/lastminutedotcom/oauth2-sso-spring-boot/master/LICENSE.md)

An example project in which we show how it is possible to implement a distributed authorization system in a microservices architecture with Spring.

### Description

This repository contains an example for our blog post "Distributed authorization in Microservices with Spring". This is a
quote from the post:

> ......In a monolithic application, it's easy to add security features. There exist an
implementations in a lot of languages of the most common security techniques and protocols like basic auth, form auth,
[LDAP](https://en.wikipedia.org/wiki/Lightweight_Directory_Access_Protocol) authentication and so on.
However in a distributed system, all of the previous techniques does not work.
First of all, due to the nature of Microservices architecture, it is a bad idea to give each microservice access to the user datasource.
In many cases (e.g. a rest service ), perform the login each time does not work. We
need a better solution. What can we use? The answer to this question is a set of advanced protocols: Kerberos, SAML
2, OAuth2 or OpenId Connect.  .......

<!---
Click [here](https://technology.lastminute.com/oauth2-sso-with-spring-boot "Distributed authorization in Microservices with Spring") to read the post.
-->
