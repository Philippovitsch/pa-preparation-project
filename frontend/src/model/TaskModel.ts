import { TagModel } from "./TagModel"

export type TaskModel = {
  id?: number,
  name: string,
  description: string,
  timestamp: string,
  tags: TagModel[]
}
