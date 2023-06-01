export type UserDetailsModel = {
    username: string,
    authorities: string[],
    accountNonExpired: boolean,
    accountNonLocked: boolean,
    credentialsNonExpired: boolean,
    enabled: boolean
}
