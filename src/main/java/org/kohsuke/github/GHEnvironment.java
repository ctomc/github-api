package org.kohsuke.github;

import java.net.URL;

/**
 * Represents a repository deployment Environment
 *
 * @see <a href="https://developer.github.com/v3/repos/deployments/">documentation</a>
 * @see GHRepository#listDeployments(String, String, String, String) GHRepository#listDeployments(String, String,
 *      String, String)
 * @see GHRepository#getDeployment(long) GHRepository#getDeployment(long)
 */
/*
 * { "name": "staging", "html_url":
 * "https://github.com/github/hello-world/deployments/activity_log?environments_filter=staging", "protection_rules": [ {
 * "id": 3736, "node_id": "MDQ6R2F0ZTM3MzY=", "type": "wait_timer", "wait_timer": 30 }, { "id": 3755, "node_id":
 * "MDQ6R2F0ZTM3NTU=", "type": "required_reviewers", "reviewers": [ { "type": "User", "reviewer": { "login": "octocat",
 * "id": 1, "node_id": "MDQ6VXNlcjE=", "avatar_url": "https://github.com/images/error/octocat_happy.gif", "gravatar_id":
 * "", "url": "https://api.github.com/users/octocat", "html_url": "https://github.com/octocat", "followers_url":
 * "https://api.github.com/users/octocat/followers", "following_url":
 * "https://api.github.com/users/octocat/following{/other_user}", "gists_url":
 * "https://api.github.com/users/octocat/gists{/gist_id}", "starred_url":
 * "https://api.github.com/users/octocat/starred{/owner}{/repo}", "subscriptions_url":
 * "https://api.github.com/users/octocat/subscriptions", "organizations_url":
 * "https://api.github.com/users/octocat/orgs", "repos_url": "https://api.github.com/users/octocat/repos", "events_url":
 * "https://api.github.com/users/octocat/events{/privacy}", "received_events_url":
 * "https://api.github.com/users/octocat/received_events", "type": "User", "site_admin": false } }, { "type": "Team",
 * "reviewer": { "id": 1, "node_id": "MDQ6VGVhbTE=", "url": "https://api.github.com/teams/1", "html_url":
 * "https://github.com/orgs/github/teams/justice-league", "name": "Justice League", "slug": "justice-league",
 * "description": "A great team.", "privacy": "closed", "permission": "admin", "members_url":
 * "https://api.github.com/teams/1/members{/member}", "repositories_url": "https://api.github.com/teams/1/repos",
 * "parent": null } } ] }, { "id": 3756, "node_id": "MDQ6R2F0ZTM3NTY=", "type": "branch_policy" } ],
 * "deployment_branch_policy": { "protected_branches": false, "custom_branch_policies": true } }
 *
 */

public class GHEnvironment extends GHObject {
    private GHRepository owner;
    protected String name;
    protected String html_url;
    protected Object protection_rules;
    protected Object deployment_branch_policy;

    GHEnvironment wrap(GHRepository owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public Object getProtection_rules() {
        return protection_rules;
    }

    public Object getDeployment_branch_policy() {
        return deployment_branch_policy;
    }

    /**
     * @see {@link org.kohsuke.github.GHObject#getHtmlUrl}
     */
    @Override
    public URL getHtmlUrl() {
        return GitHubClient.parseURL(html_url);
    }

    // test only
    GHRepository getOwner() {
        return owner;
    }
}
