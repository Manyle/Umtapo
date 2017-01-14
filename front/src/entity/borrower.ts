import {Library} from './library';
import {Address} from './address';
import {Subscription} from './subscription';
import {Loan} from './loan';

export class Borrower {
  id: number;
  private name: string;
  private birthday: Date;
  private quota: number;
  private emailOptin: boolean;
  private comment: string;
  private address: Address;
  subscriptions: Subscription[];
  private loans: Array<Loan>;
  private library: Library;

  getName(): string {
    return this.name;
  }

  setName(value: string) {
    this.name = value;
  }

  getBirthday(): Date {
    return this.birthday;
  }

  setBirthday(value: Date) {
    this.birthday = value;
  }

  getQuota(): number {
    return this.quota;
  }

  setQuota(value: number) {
    this.quota = value;
  }

  getEmailOptin(): boolean {
    return this.emailOptin;
  }

  setEmailOptin(value: boolean) {
    this.emailOptin = value;
  }

  getAddress(): Address {
    return this.address;
  }

  setAddress(value: Address) {
    this.address = value;
  }


  getSubscriptions(): Subscription[] {
    return this.subscriptions;
  }

  setSubscriptions(value: Subscription[]) {
    this.subscriptions = value;
  }

  getLoans(): Array<Loan> {
    return this.loans;
  }

  setLoans(value: Array<Loan>) {
    this.loans = value;
  }

  getLibrary(): Library {
    return this.library;
  }

  setLibrary(value: Library) {
    this.library = value;
  }

  getComment(): string {
    return this.comment;
  }

  setComment(value: string) {
    this.comment = value;
  }
}
