import { TagModel } from "./TagModel"

export type TaskModel = {
  id?: number,
  username: string,
  name: string,
  description: string,
  timestamp: string,
  tags: TagModel[]
}
