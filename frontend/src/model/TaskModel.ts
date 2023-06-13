import { TagModel } from "./TagModel"
import {UserModel} from "./UserModel.ts";

export type TaskModel = {
  id?: number,
  user: UserModel | string,
  name: string,
  description: string,
  timestamp: string,
  tags: TagModel[]
}
