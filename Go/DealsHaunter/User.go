package main

import (
	"gopkg.in/telebot.v3"
	"gorm.io/gorm"
)

type User struct {
	gorm.Model

	ID        int64  `json:"id"`
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	Username  string `json:"username"`
}

func NewUser(user *telebot.User) *User {
	return &User{
		ID:        user.ID,
		FirstName: user.FirstName,
		LastName:  user.LastName,
		Username:  user.Username,
	}
}
