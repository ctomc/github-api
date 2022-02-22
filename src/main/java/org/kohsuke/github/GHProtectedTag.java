package org.kohsuke.github;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represents a tag in {@link GHRepository}
 *
 * @see GHRepository#listTags() GHRepository#listTags()
 */
@SuppressFBWarnings(value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD", "NP_UNWRITTEN_FIELD" },
        justification = "JSON API")
public class GHProtectedTag extends GitHubInteractiveObject {
    private GHRepository owner;

    private long id;
    private String pattern;

    GHProtectedTag wrap(GHRepository owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP" }, justification = "Expected behavior")
    public GHRepository getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "GHProtectedTag{" + "owner=" + owner + ", id=" + id + ", pattern='" + pattern + '\'' + '}';
    }
}
