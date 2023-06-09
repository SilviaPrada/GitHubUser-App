package com.dicoding.githubuser.data.retrofit

import com.google.gson.annotations.SerializedName

data class GitHubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(


	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

//	@field:SerializedName("followers_url")
//	val followersUrl: String,
//
//	@field:SerializedName("type")
//	val type: String,
//
//	@field:SerializedName("url")
//	val url: String,
//
//	@field:SerializedName("subscriptions_url")
//	val subscriptionsUrl: String,
//
//	@field:SerializedName("score")
//	val score: Any,
//
//	@field:SerializedName("received_events_url")
//	val receivedEventsUrl: String,
//
//	@field:SerializedName("events_url")
//	val eventsUrl: String,
//
//	@field:SerializedName("html_url")
//	val htmlUrl: String,
//
//	@field:SerializedName("site_admin")
//	val siteAdmin: Boolean,
//
//	@field:SerializedName("gravatar_id")
//	val gravatarId: String,
//
//	@field:SerializedName("node_id")
//	val nodeId: String,
//
//	@field:SerializedName("organizations_url")
//	val organizationsUrl: String
)
