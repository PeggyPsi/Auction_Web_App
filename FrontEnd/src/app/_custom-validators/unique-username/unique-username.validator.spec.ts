import { UniqueUsernameValidator } from "./unique-username.validator";


describe('UniqueUsername', () => {
  it('should create an instance', () => {
    expect(new UniqueUsernameValidator()).toBeTruthy();
  });
});
