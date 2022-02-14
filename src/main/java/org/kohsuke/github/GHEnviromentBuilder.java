package org.kohsuke.github;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type GHDeploymentBuilder.
 */
// Based on https://docs.github.com/en/rest/reference/deployments#create-or-update-an-environment
public class GHEnviromentBuilder {
    private final GHRepository repo;
    private final Requester builder;
    private final List<Reviewer> reviewers = new LinkedList<>();
    private boolean enableProtectedBranches = false;
    private boolean enableCustomBranchPolicy = false;

    /**
     * Instantiates a new Gh deployment builder.
     *
     * @param repo
     *            the repo
     */
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP2" }, justification = "Acceptable")
    public GHEnviromentBuilder(GHRepository repo) {
        this.repo = repo;
        this.builder = repo.root()
                .createRequest()
                /*
                 * .withPreview(Previews.ANT_MAN) .withPreview(Previews.FLASH)
                 */
                .method("PUT");
    }

    /**
     * Instantiates a new Gh deployment builder.
     *
     * @param repo
     *            the repo
     * @param name
     *            the name of environment
     */
    public GHEnviromentBuilder(GHRepository repo, String name) {
        this(repo);
        name(name);
    }

    /**
     * environment_name gh deployment builder.
     *
     * @param name
     *            environment name
     * @return the gh deployment builder
     */
    public GHEnviromentBuilder name(String name) {
        builder.withUrlPath(repo.getApiTailUrl("environments/" + name));
        return this;
    }

    /**
     * Wait timer gh deployment builder.
     *
     * @param minutes
     *            The amount of time to delay a job after the job is initially triggered. The time (in minutes) must be
     *            an integer between 0 and 43,200 (30 days).
     * @return the gh deployment builder
     */
    public GHEnviromentBuilder waitTimer(int minutes) {
        builder.with("wait_timer", minutes);
        return this;
    }

    public GHEnviromentBuilder addReviewer(GHTeam reviewer) {
        reviewers.add(new Reviewer("Team", reviewer.getId()));
        return this;
    }

    public GHEnviromentBuilder addReviewer(GHUser reviewer) {
        reviewers.add(new Reviewer("User", reviewer.getId()));
        return this;
    }

    public GHEnviromentBuilder enableProtectedBranches() {
        this.enableProtectedBranches = true;
        return this;
    }

    public GHEnviromentBuilder enableCustomBranchPolicy() {
        this.enableCustomBranchPolicy = true;
        return this;
    }

    /**
     * Create gh deployment.
     *
     * @return the gh deployment
     * @throws IOException
     *             the io exception
     */
    public GHEnvironment create() throws IOException {
        if (enableCustomBranchPolicy || enableProtectedBranches) {
            builder.with("deployment_branch_policy",
                    new DeploymentBranchPolicy(enableProtectedBranches, enableCustomBranchPolicy));
        }
        builder.with("reviewers", reviewers);
        return builder.fetch(GHEnvironment.class).wrap(repo);
    }

    private static class Reviewer {
        /**
         * The type of reviewer. Must be one of: User or Team
         */
        final String type;

        /**
         * The id of the user or team who can review the deployment
         */
        final long id;

        public Reviewer(String type, long id) {
            this.type = type;
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public long getId() {
            return id;
        }
    }

    private static class DeploymentBranchPolicy {
        private final boolean protectedBranches;
        private final boolean customBranchPolicies;

        public DeploymentBranchPolicy(boolean protectedBranches, boolean customBranchPolicies) {
            this.protectedBranches = protectedBranches;
            this.customBranchPolicies = customBranchPolicies;
        }

        public boolean isProtectedBranches() {
            return protectedBranches;
        }

        public boolean isCustomBranchPolicies() {
            return customBranchPolicies;
        }
    }
}
