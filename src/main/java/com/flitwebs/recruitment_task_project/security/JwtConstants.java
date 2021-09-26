package com.flitwebs.recruitment_task_project.security;

public class JwtConstants {

  public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60;
  public static final String SIGNING_KEY = "flitWebs_sample_app";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String AUTHORITIES_KEY = "scopes";
}
