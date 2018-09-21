package com.example.domain.exceptions

sealed class Failure {
    //specify domain specific failures
    class ServerError : Failure()

    class NoResponse : Failure()
    //extend this class for feature specific failures
    abstract class FeatureFailure : Failure()
}